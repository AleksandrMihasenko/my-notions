import { getClassNames } from './classNames';

describe('classNames', () => {
    test('Should return only one class with one param', () => {
        expect(getClassNames('someClass')).toBe('someClass');
    })

    test('Should return several classes with several params', () => {
        expect(getClassNames('someClass', {}, ['additionalClass'])).toBe('someClass additionalClass');
    })

    test('Should return several classes with several params and additional mod true', () => {
        expect(getClassNames('someClass', {mode: true}, ['additionalClass'])).toBe('someClass additionalClass mode');
    })

    test('Should return several classes with several params and additional mod false', () => {
        expect(getClassNames('someClass', {mode: false}, ['additionalClass'])).toBe('someClass additionalClass');
    })

    test('Should return several classes with several params and additional mod undefined', () => {
        expect(getClassNames('someClass', {mode: false}, ['additionalClass'])).toBe('someClass additionalClass');
    })
});