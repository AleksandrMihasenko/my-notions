import 'app/styles/index.scss';
import { StoryFn } from '@storybook/react'
import { Theme, ThemeProvider } from 'app/providers/ThemeProvider';

/* eslint-disable react/display-name */
export const themeDecorator = (theme: Theme) => (StoryComponent: StoryFn) => (
    <ThemeProvider>
        <div className={`app ${theme}`}>
            <StoryComponent/>
        </div>
    </ThemeProvider>
);
