import { classNames } from './classNames.ts';

describe('classNames', () => {
	test('with only first param', () => {
		expect(classNames('someClass')).toBe('someClass');
	})

	test('with additional class', () => {
		expect(classNames('someClass', {}, ['mockedClass'])).toBe('someClass mockedClass');
	})

	test('with additional class and mods', () => {
		expect(classNames('someClass', { 'hovered': true, 'clicked': false }, ['mockedClass'])).toBe('someClass mockedClass hovered');
	})
});
