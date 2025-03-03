import React from 'react';
import { render, screen } from '@testing-library/react';
import Planner from '../../../src/components/Trip/Planner';
import { beforeEach, describe, test, jest, expect } from '@jest/globals';

describe('Planner', () => {
	const plannerProps = {
		createSnackBar: jest.fn(),
		places: [],
		selectedIndex: -1,
		placeActions: {
			append: jest.fn(),
		},
		distances: {leg:[1,1,1,1,1,1,1,1], cumulative:[1,2,3,4,5,6,7,8], total: 8}
	};

	beforeEach(() => {
		render(<Planner {...plannerProps} />);
	});

	test('base: renders a Leaflet map', async () => {
		screen.getByText('Leaflet');
	});

	test('base: renders trip table', async () => {
		screen.getByTestId('trip-header-title');
	});
});
