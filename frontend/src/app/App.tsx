import { Suspense } from 'react';
import { AppRouter } from '@/app/providers/Router';
import { Navbar } from '@/widgets/Navbar';
import { Sidebar } from '@/widgets/Sidebar';
import { useTheme } from '@/app/providers/ThemeProvider';
import { classNames } from '@/shared';
import './styles/index.scss'

export const App = () => {
	const { theme } = useTheme();

	return (
		<div className={classNames('app', {}, [theme])}>
			<Suspense fallback={null}>
				<Navbar />

				<div className="content-page">
					<Sidebar />
					<AppRouter />
				</div>
			</Suspense>
		</div>
	);
}
