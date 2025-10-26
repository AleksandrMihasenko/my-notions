import { ReactNode } from 'react';
import { classNames, Portal } from '@/shared';
import cls from './Modal.module.scss';

interface ModalProps {
	className?: string;
	children?: ReactNode;
	isOpen?: boolean;
	onClose?: () => void;
}

const Modal = (props: ModalProps) => {
	const { className, children, isOpen, onClose } = props;

	const mods: Record<string, boolean | undefined> = {
		[cls['opened']]: isOpen
	}

	const closeHandler = () => {
		if (onClose) {
			onClose();
		}
	}

	const contentClickHandler = (e: React.MouseEvent) => {
		e.stopPropagation();
	}

	return (
		<Portal>
			<div className={classNames(cls['modal'], mods, [className])}>
				<div className={cls['overlay']} onClick={closeHandler}>
					<div className={cls['content']} onClick={contentClickHandler}>
						{ children }
						Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur, itaque.
					</div>
				</div>
			</div>
		</Portal>
	);
}

export default Modal;
