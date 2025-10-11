import { configureStore } from '@reduxjs/toolkit';
import { StateSchema } from './StateSchema.ts';

export function createReduxStore(initialState?: StateSchema) {
	return configureStore<StateSchema>({
		reducer: {},
		preloadedState: initialState,
	});
}
