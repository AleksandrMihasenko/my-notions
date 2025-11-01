import React from 'react';
import { classNames, Modal } from '@/shared';
import LoginForm from '../LoginForm/LoginForm';

interface LoginModalProps {
	className?: string;
	isOpen: boolean;
	onClose: () => void;
}

const LoginModal = ({ className, isOpen, onClose }: LoginModalProps) => {
	return (
		<Modal
			className={classNames(cls['login-modal'], {}, [className])}
			isOpen={isOpen}
			onClose={onClose}
		>
			<LoginForm />
		</Modal>
	);
}

export default LoginModal;