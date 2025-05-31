export async function baseFetch(url: string, options: RequestInit = {}) {
	const res = await fetch(import.meta.env.VITE_API_URL + url, {
		...options,
		headers: {
			'Content-Type': 'application/json',
			...options.headers,
		},
	});

	if (!res.ok) {
		throw new Error(await res.text());
	}

	return res.json();
}
