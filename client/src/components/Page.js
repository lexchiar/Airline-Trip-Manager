import React, { useEffect, useState } from 'react';
import { Collapse } from 'reactstrap';
import Header from './Header/Header';
import About from './About/About';
import Planner from './Trip/Planner';
import { useToggle } from '../hooks/useToggle';
import { usePlaces } from '../hooks/usePlaces';
import { useDistances } from '../hooks/useDistances';
import { useFind } from '../hooks/useFind';
import { useServerSettings } from '../hooks/useServerSettings';
import { useTour } from '../hooks/useTour';

export default function Page(props) {
	const [showAbout, toggleAbout] = useToggle(false);
	const [serverSettings, processServerConfigSuccess] = useServerSettings(props.showMessage);
	const { places, selectedIndex, placeActions } = usePlaces();
	const [tripName, setTripName] = useState('My Trip');
	const [radius, setRadius] = useState(3958.8);
	const { distances, distanceActions } = useDistances(places, radius, serverSettings.serverUrl);
	const [match, setMatch] = useState("");
	const {find,findActions} = useFind(match, 5, serverSettings.serverUrl);
	const {tour, tourActions} = useTour(3958.8, places, {setPlaces:placeActions.setPlaces}, 1, serverSettings.serverUrl);

	const context = {
		showAbout, toggleAbout,
		serverSettings, processServerConfigSuccess,
		places, placeActions,
		tripName, setTripName,
		disableRemoveAll : !places?.length,
		selectedIndex,
		distances,
		distanceActions,
		setRadius,
		setMatch,
		findActions,
		find,
		tour,
		tourActions
	}

	return (
		<>
			<Header 
				{...context}
				showMessage={props.showMessage}
			/>
			<MainContentArea 
				{...context}
			/>
		</>
	);
}

function MainContentArea(props) {
	return (
		<div className='body'>
			<Collapse isOpen={props.showAbout}>
				<About closePage={props.toggleAbout}/>
			</Collapse>
			<Collapse isOpen={!props.showAbout} data-testid='planner-collapse'>
				<Planner {...props}/>
			</Collapse>
		</div>
	);
}
