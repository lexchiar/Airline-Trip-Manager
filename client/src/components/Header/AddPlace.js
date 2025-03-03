import React, { useEffect, useState } from 'react';
import {
	Button,
	Col,
	Modal,
	ModalBody,
	ModalHeader,
	Input,
	Collapse,
	ModalFooter,
} from 'reactstrap';
import Coordinates from 'coordinate-parser';
import { reverseGeocode } from '../../utils/reverseGeocode';
import { BsDice6 } from 'react-icons/bs';

export default function AddPlace(props) {
	const [foundPlace, setFoundPlace] = useState();
	const [coordString, setCoordString] = useState('');
	const [inputString, setInputString] = useState('');
	const addPlaceProps = {
		foundPlace,
		setFoundPlace,
		coordString,
		setCoordString,
		inputString,
		setInputString,
		setMatch: props.setMatch,
		match: props.match,
		places: props.find.places
	}
	return (
		<Modal isOpen={props.showAddPlace} toggle={props.toggleAddPlace}>
			<AddPlaceHeader toggleAddPlace={props.toggleAddPlace} />
			<PlaceSearch {...addPlaceProps} append={props.placeActions.append}/>
			<AddPlaceFooter
				{...addPlaceProps}
				{...props}
				append={props.placeActions.append}/>
		</Modal>
	);
}

function AddPlaceHeader(props) {
	return (
		<ModalHeader className='ml-2' toggle={props.toggleAddPlace}>
			Add a Place
		</ModalHeader>
	);
}

function PlaceSearch(props) {
	useEffect(() => {
		searchForPlace(props.inputString, props.setFoundPlace, props.setMatch);
	}, [props.inputString]);
	return (
		<ModalBody>
			<Col>
				<Input
					onChange={(input) => props.setInputString(input.target.value)}
					placeholder='Search by Coordinates or Name'
					data-testid='search-input'
					value={props.inputString}
				/>
				<DisplayTable 
					foundPlace={props.foundPlace}
					places={props.places}
					append={props.append}
				/>
			</Col>
		</ModalBody>
	);
}

function DisplayTable(props) {
	if (props.places.length !== 0) {
		return getTable(props.places, props.append);
	}
	else if (props.foundPlace !== undefined) {
		return getTable([props.foundPlace], props.append);
	}
	else {
		return null;
	}
}

function getTable(places, append) {
	return (
		<table>
			<TableHeader/>
			<tbody>
				{
					places.map((place, index) => (
						<tr key = {place.id}>
							<PlaceName place={place}/>
							<PlaceLocation place={place}/>
							<td> <Button 
									color='primary'
									onClick={() => {
										append(place);
				
									}}
									data-testid='add-place-button'
									> 
									Add </Button>
							</td>
						</tr>
						))
				}
			</tbody>
		</table>
	);
}

function PlaceName(props) {
	if (props.place.hasOwnProperty('defaultDisplayName')) {
		return (<td> {props.place.defaultDisplayName} </td>);
	} else {
		return (<td> {props.place.name} </td>);
	}
}

function PlaceLocation(props) {
	if (props.place.municipality !== undefined && props.place.country !== undefined) {
		return (<td> {props.place.municipality}, {props.place.country} </td>);
	}
	return (<td>{props.place.municipality}{props.place.country}</td>);
}

function TableHeader() {
	return (
		<thead>
			<tr>
				<th> Name </th>
				<th> Location </th>
			</tr>
		</thead>
	);
}

async function searchForPlace(inputString, setFoundPlace, setMatch) {
	try {
		const latLngPlace = new Coordinates(inputString);
		const lat = latLngPlace.getLatitude();
		const lng = latLngPlace.getLongitude();
		if (isLatLngValid(lat,lng)) {
			const fullPlace = await reverseGeocode({ lat, lng });
			setFoundPlace(fullPlace);
		}
	} catch (error) {
		setFoundPlace(undefined);
		if (isSearchByText(inputString)) {
			setMatch(inputString)
		}
	}
}

/*
function PlaceInfo(props) {
	return (
		<Collapse isOpen={!!props.foundPlace}>
			<br />
			{props.foundPlace?.formatPlace()}
		</Collapse>
	);
}
*/

function AddPlaceFooter(props) {
	return (
		<ModalFooter>
			<Button
				color='primary'
				onClick={() => {
					props.findActions.random();
				}}
				data-testid='random-button'
			>
				<BsDice6></BsDice6>
			</Button>
		</ModalFooter>
	);
}

function isLatLngValid(lat,lng) {
	return (lat !== undefined && lng !== undefined);
}

function isSearchByText(inputString) {
	return !(/^[0-9]+$/.test(inputString));
}