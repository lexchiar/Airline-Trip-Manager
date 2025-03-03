import React from 'react';
import { Container, Button } from 'reactstrap';
import { CLIENT_TEAM_NAME } from '../../utils/constants';
import Menu from './Menu';
import { useToggle } from '../../hooks/useToggle';
import AddPlace from './AddPlace';
import LoadFile from './LoadFile'
import ServerSettings from './ServerSettings';
import { IoMdClose } from 'react-icons/io';

export default function Header(props) {
	const [showAddPlace, toggleAddPlace] = useToggle(false);
	const [showServerSettings, toggleServerSettings] = useToggle(false);
	const [showLoadFile, toggleLoadFile] = useToggle(false);

	const toggles = {
		toggleAddPlace, toggleServerSettings, toggleLoadFile, toggleAbout: props.toggleAbout,
	}

	const shows = {
		showAddPlace, showServerSettings, showLoadFile, showAbout: props.showAbout
	}
	
	return (
		<React.Fragment>
			<HeaderContents
				{...toggles}
				{...props}
			/>
			<AppModals
				{...shows}
				{...toggles}
				{...props}
			/>
		</React.Fragment>
	);
}

function HeaderContents(props) {
	return (
		<div className='full-width header vertical-center'>
			<Container>
				<div className='header-container'>
					<h1
						className='tco-text-upper header-title'
						data-testid='header-title'
					>
						{CLIENT_TEAM_NAME}
					</h1>
					<HeaderButton {...props} />
				</div>
			</Container>
		</div>
	);
}

function HeaderButton(props) {
	return props.showAbout ? (
		<Button
			data-testid='close-about-button'
			color='primary'
			onClick={() => props.toggleAbout()}
		>
			<IoMdClose size={32} />
		</Button>
	) : (
		<Menu {...props}/>
	);
}

function AppModals(props) {
	return (
		<>
			<AddPlace
				{...props}
			/>
			<ServerSettings
				{...props}
			/>
			<LoadFile
				{...props}
			/>
		</>
	);
}
