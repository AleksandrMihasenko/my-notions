import type { RouteProps } from 'react-router-dom';
import { MainPage, NoteListPage, NotFoundPage } from '@/pages';

export enum AppRoutes {
	MAIN = 'main',
	NOTES = 'notes',
	NOT_FOUND = 'not_found',
}

export const RoutePath: Record<AppRoutes, string> = {
	[AppRoutes.MAIN]: '/',
	[AppRoutes.NOTES]: '/notes',
	[AppRoutes.NOT_FOUND]: '*',
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
	[AppRoutes.NOT_FOUND]: {
		path: RoutePath.not_found,
		element: <NotFoundPage />,
	}  as RouteProps,
}
