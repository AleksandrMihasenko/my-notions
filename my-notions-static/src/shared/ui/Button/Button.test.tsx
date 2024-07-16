import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';
import { Button, ButtonTheme } from './Button';

describe('Button.tsx', () => {
    test('Should render component correctly', () => {
        render(<Button>Test</Button>);
        expect(screen.getByText('Test')).toBeInTheDocument();
    })

    test('Should render component correctly with clear theme', () => {
        render(<Button className='test' theme={ButtonTheme.CLEAR}>Test</Button>);
        expect(screen.getByText('Test')).toHaveClass('clear');
    })
});
