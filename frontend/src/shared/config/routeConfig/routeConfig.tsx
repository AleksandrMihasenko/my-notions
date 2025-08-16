import type { RouteProps } from 'react-router-dom';
import { MainPage, NoteListPage } from '@/pages';

export enum AppRoutes {
	MAIN = 'main',
	NOTES = 'notes',
}

export const RoutePath: Record<AppRoutes, string> = {
	[AppRoutes.MAIN]: '/',
	[AppRoutes.NOTES]: '/notes',
}

export const routeConfig: Record<AppRoutes, RouteProps> = {
	[AppRoutes.MAIN]: {
		path: RoutePath.main,
		element: <MainPage />,
	} as RouteProps,
	[AppRoutes.NOTES]: {
		path: RoutePath.notes,
		element: <NoteListPage />,
	}  as RouteProps,
}
