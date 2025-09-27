import { classNames } from '@/shared';
import cls from './Loader.module.scss';

const Loader = () => {
	return (
		<div className={classNames(cls['loader'], {}, [])} />
	);
}

export default Loader;
