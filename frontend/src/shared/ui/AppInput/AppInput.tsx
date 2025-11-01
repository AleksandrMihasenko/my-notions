import { ChangeEvent, InputHTMLAttributes, memo } from 'react';
import { classNames } from '@/shared';
import cls from './AppInput.module.scss';

type HTMLInputProps = Omit<InputHTMLAttributes<HTMLInputElement>, 'value' | 'onChange'>

interface AppInputProps extends HTMLInputProps {
	className?: string;
	value?: string | number;
	type?: string;
	placeholder?: string;
	id?: string;
	onChange?: (value: string) => void;
}

const AppInput = (props: AppInputProps) => {
	const {
		className,
		value,
		type = 'text',
		placeholder = '',
		id = '',
		onChange,
		...otherProps
	} = props;

	const onChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
		onChange?.(e.target.value)
	}

	return (
		<div
			className={classNames(cls['app-input-wrapper'], {}, [className])}
			{ ...otherProps }
		>
			<input
				id={id}
				type={type}
				value={value}
				placeholder={placeholder}
				onChange={onChangeHandler}
				className={classNames(cls['app-input'], {}, [className])}
			/>
		</div>
	);
}

const MemoizedAppInput = memo(AppInput);

export default MemoizedAppInput;
