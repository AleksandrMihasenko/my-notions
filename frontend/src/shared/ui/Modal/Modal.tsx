import { ReactNode, useEffect, useState } from 'react';
import { classNames, Portal } from '@/shared';
import cls from './Modal.module.scss';

interface ModalProps {
	className?: string;
	children?: ReactNode;
	isOpen?: boolean;
	lazy?: boolean;
	onClose?: () => void;
}

const Modal = (props: ModalProps) => {
	const { className, children, isOpen, lazy, onClose } = props;

	const mods: Record<string, boolean | undefined> = {
		[cls['opened']]: isOpen
	}

	const [isMounted, setIsMounted] = useState(false)

	const closeHandler = () => {
		if (onClose) {
			onClose();
		}
	}

	const contentClickHandler = (e: React.MouseEvent) => {
		e.stopPropagation();
	}

	useEffect(() => {
		if (isOpen) {
			setIsMounted(true);
		}
	}, [isOpen]);

	if (lazy && !isMounted) {
		return null
	}

	return (
		<Portal>
			<div className={classNames(cls['modal'], mods, [className])}>
				<div className={cls['overlay']} onClick={closeHandler}>
					<div className={cls['content']} onClick={contentClickHandler}>
						{ children }
					</div>
				</div>
			</div>
		</Portal>
	);
}

export default Modal;
