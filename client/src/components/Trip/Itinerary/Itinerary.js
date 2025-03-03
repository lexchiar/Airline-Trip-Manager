import React, { useState } from 'react';
import { useToggle } from '../../../hooks/useToggle';
import { Table, Collapse } from 'reactstrap';
import { latLngToText, placeToLatLng } from '../../../utils/transformers';
import { BsChevronDown } from 'react-icons/bs';
import PlaceActions from './PlaceActions';
import { Button } from 'reactstrap';
import { Dropdown, DropdownMenu, DropdownItem, DropdownToggle } from 'reactstrap';
import { FaArrowsAltH } from 'react-icons/fa';
import { TbSum } from 'react-icons/tb';

export default function Itinerary(props) {
	const placeListProps = {
		places: props.places,
		placeActions: props.placeActions,
		selectedIndex: props.selectedIndex,
		distances: props.distances,
		distanceActions: props.distanceActions,
		setRadius:props.setRadius
	}
	
	return (
		<Table responsive>
			<TripHeader
				tripName={props.tripName}
				totalDistance={props.distances.total}
				{...props}
			/>
			<PlaceList 
				{...placeListProps}
			/>
		</Table>
	);
}

function TripHeader(props) {
	const [selectedOption, setSelectedOption] = useState('mi');
	const [dropdownOpen, setDropdownOpen] = useState(false);
	const toggle = () => setDropdownOpen((prevState) => !prevState);
	const handleOptionChange = (event) => {
		setSelectedOption(event.target.value);
		if(event.target.value == "km"){
			props.setRadius(6371.1);
		}
		else if(event.target.value == "nm"){
			props.setRadius(3440.1);
		}
		else if(event.target.value == "mi"){
			props.setRadius(3958.8);
		}
	  };
	return (
		<thead>
			<tr>
				<th className='trip-header-title' data-testid='trip-header-title'>
					{props.tripName}
					<p>
						<th>
						Total Distance: {props.totalDistance}
						</th>
						<th>
							<Dropdown isOpen={dropdownOpen} toggle={toggle} direction="down" id="dropMenu">
								<DropdownToggle caret color='primary'>
									{selectedOption}
								</DropdownToggle>
								<DropdownMenu name ="units" color='primary'>
									<DropdownItem name="Miles" value ="mi" onClick={handleOptionChange}>Miles</DropdownItem>
									<DropdownItem name="Kilometers" value ="km" onClick={handleOptionChange}>Kilometers</DropdownItem>
									<DropdownItem name="Nautical Miles" value ="nm" onClick={handleOptionChange}>Nautical Miles</DropdownItem>
								</DropdownMenu>
							</Dropdown>
						</th>
						<th>
							<Button style={{align:'right'}}
								color='primary'
								onClick={async() => {
									props.tourActions.optimize()
								}}
								>
								Optimize
							</Button>
						</th>
					</p>
					
				</th>
				<th></th>
				<th className='trip-header-leg' data-testid='trip-header-leg' style={{textAlign:'center'}}>
					<FaArrowsAltH/>				
				</th>
				<th className='trip-header-cumulative' data-testid='trip-header-cumulative' style={{textAlign:'center'}}>
					<TbSum/>
				</th>
			</tr>
		</thead>
	);
}

function PlaceList(props) {
	return (
		<tbody>
			{props.places.map((place, index) => (
				<PlaceRow
					{...props}
					key={`table-${JSON.stringify(place)}-${index}`}
					cumulativeDistance={props.distances.cumulative[index]}
					legDistance={props.distances.leg[index]}
					place={place}
					index={index}
				/>
			))}
		</tbody>
	);
}

function PlaceRow(props) {
	const [showFullName, toggleShowFullName] = useToggle(false);
	const name = props.place.defaultDisplayName;
	const location = latLngToText(placeToLatLng(props.place));
	return (
		<tr className={props.selectedIndex === props.index ? 'selected-row' : ''}>
			<td
				data-testid={`place-row-${props.index}`}
				onClick={() =>
					placeRowClicked(
						toggleShowFullName,
						props.placeActions.selectIndex,
						props.index
					)
				}
			>
				<strong>{name}</strong>
				<AdditionalPlaceInfo {...props} showFullName={showFullName} location={location}/>
			</td>
			<td><RowArrow toggleShowFullName={toggleShowFullName} index={props.index}/></td>
			<td style={{textAlign:'center'}}>{props.legDistance}</td>
			<td style={{textAlign:'center'}}>{props.cumulativeDistance}</td>
			
			
		</tr>
	);
}

function AdditionalPlaceInfo(props) {
	return (
		<Collapse isOpen={props.showFullName}>
			{props.place.formatPlace().replace(`${props.place.defaultDisplayName}, `, '')}
			<br />
			{props.location}
			<br />
			<PlaceActions placeActions={props.placeActions} index={props.index} />
		</Collapse>
	);
}

function placeRowClicked(toggleShowFullName, selectIndex, placeIndex) {
	toggleShowFullName();
	selectIndex(placeIndex);
}

function RowArrow(props) {
	return (
		<td>
			<BsChevronDown data-testid={`place-row-toggle-${props.index}`} onClick={props.toggleShowFullName}/>
		</td>
	);
}
