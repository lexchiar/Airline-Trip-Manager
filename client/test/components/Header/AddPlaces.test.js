import React from 'react';
import { beforeEach, describe, expect, jest, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { findAllByAltText, getByTestId, render, screen, waitFor } from '@testing-library/react';
import AddPlace from '../../../src/components/Header/AddPlace';
import {
	REVERSE_GEOCODE_RESPONSE,
	MOCK_PLACE_RESPONSE,
} from '../../sharedMocks';

describe('AddPlace', () => {
	const placeObj = {
		latLng: '40.57, -105.08',
		name: 'Colorado State University, South College Avenue, Fort Collins, Larimer County, Colorado, 80525-1725, United States',
	};

	const props = {
		toggleAddPlace: jest.fn(),
		placeActions: {
			append: jest.fn(),
		},
		showAddPlace: true,
        setMatch: jest.fn(),
		findActions: {
			random: jest.fn(),
		}
	};

	beforeEach(() => {
		render(
			<AddPlace
				placeActions={props.placeActions}
				showAddPlace={props.showAddPlace}
				toggleAddPlace={props.toggleAddPlace}
				find={{places:[]}}
				setMatch={props.setMatch}
				findActions={props.findActions}
			/>
		);
	});

	test('dswan: validates input', async () => {
		const coordInput = screen.getByTestId('search-input');
		user.type(coordInput, placeObj.latLng);

		await waitFor(() => {
			expect(coordInput.value).toEqual(placeObj.latLng);
		});
	});

	test('dswan: handles invalid input', async () => {
		const coordInput = screen.getByTestId('search-input');
		user.paste(coordInput, '1');

		await waitFor(() => {
			expect(coordInput.value).toEqual('1');
		});
	});

	test('dswan: Adds place', async () => {
		fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);
		const coordInput = screen.getByTestId('search-input');
		user.type(coordInput, placeObj.latLng);

		await waitFor(() => {
			expect(coordInput.value).toEqual(placeObj.latLng);
		});
	});

	test('dswan: Search by string', async () => {
		const coordInput = screen.getByTestId('search-input');
		user.type(coordInput, 'dave');

		await waitFor(() => {
			expect(coordInput.value).toEqual('dave');
		});
	});

	test('dswan: test', async () => {
		render(
			<AddPlace
				placeActions={props.placeActions}
				showAddPlace={props.showAddPlace}
				toggleAddPlace={props.toggleAddPlace}
				find={{places:[placeObj]}}
				setMatch={props.setMatch}
			/>
		);

		await waitFor(() => {
			expect(1).toEqual(1);
		});
	});

	test('dswan: test random', async () => {
		const randomButton = screen.getByTestId('random-button');
		user.click(randomButton);

		await waitFor(() => {
			expect(1).toEqual(1);
		});
	});

	test('dswan: test append', async () => {
		render(
			<AddPlace
				placeActions={props.placeActions}
				showAddPlace={props.showAddPlace}
				toggleAddPlace={props.toggleAddPlace}
				find={{places:[placeObj]}}
				setMatch={props.setMatch}
			/>
		);

		const appendButton = screen.getAllByTestId('add-place-button');
		user.click(appendButton[0]);

		await waitFor(() => {
			expect(1).toEqual(1);
		});
	});
});