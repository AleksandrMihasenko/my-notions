import { Loader, classNames } from '@/shared';
import cls from './PageLoader.module.scss';

interface PageLoaderProps {
	className?: string;
}

const PageLoader = ({ className }: PageLoaderProps) => {
	return (
		<div className={classNames(cls['page-loader'], {}, [className])}>
			<Loader />
		</div>
	);
}

export default PageLoader;
