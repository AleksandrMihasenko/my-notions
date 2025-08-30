import './styles/index.scss'
import { useTheme } from '@/app/providers/ThemeProvider';
import { classNames } from '@/shared';
import { AppRouter } from '@/app/providers/Router';
import { Navbar } from '@/widgets/Navbar';
import { Sidebar } from '@/widgets/Sidebar';

export const App = () => {
	const { theme } = useTheme();

	return (
		<div className={classNames('app', {}, [theme as string])}>
			<Navbar />

			<div className="content-page">
				<Sidebar />
				<AppRouter />
			</div>
		</div>
	)
}
