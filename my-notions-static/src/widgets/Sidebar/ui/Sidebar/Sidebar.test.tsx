import '@testing-library/jest-dom';
import { screen, fireEvent } from '@testing-library/react';
import { Sidebar } from './Sidebar';
import { renderWithTranslations } from 'shared/lib/renderWithTranslations/renderWithTranslations';

describe('Sidebar.tsx', () => {
    test('Should render component correctly', () => {
        renderWithTranslations(<Sidebar />);

        expect(screen.getByTestId('sidebar')).toBeInTheDocument();
    })

    test('Should expand sidebar correctly by clicking button', () => {
        renderWithTranslations(<Sidebar />);
        const toggleButton = screen.getByTestId('sidebar-toggle');

        expect(screen.getByTestId('sidebar')).toBeInTheDocument();

        fireEvent.click(toggleButton);
    })
});
