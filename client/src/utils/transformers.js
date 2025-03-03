export function latLngToText(latLng, precision = 2) {
	const lat = latLng?.lat ?? latLng?.latitude;
	const lng = latLng?.lng ?? latLng?.longitude;
    return latLng && lat !== undefined && lng !== undefined ? `${lat.toFixed(precision)}°${evaluateLat(lat)}, ${lng.toFixed(precision)}°${evaluateLng(lng)}` : "";
}

export function placeToLatLng(place) {
    return place && place.latitude !== undefined && place.longitude !== undefined ? { lat: parseFloat(place.latitude), lng: parseFloat(place.longitude) } : place;
}

export function latLngToPlace(latLng) {
    return latLng && latLng.lat !== undefined && latLng.lng !== undefined ? { latitude: latLng.lat.toString(), longitude: latLng.lng.toString() } : latLng;
}

function evaluateLat(lat) {
	return evaluate(lat, 'N', 'S');
}

function evaluateLng(lng) {
	return evaluate(lng, 'E', 'W');
}

function evaluate(val, valIfGreater, valIfLess) {
	if (val > 0) {
		return valIfGreater;
	} else if (val < 0) {
		return valIfLess;
	} else {
		return '';
	}
}