import type { Preview } from '@storybook/react';
import { styleDecorator } from '../../src/shared/config/storybook/styleDecorator/styleDecorator';
import { themeDecorator } from '../../src/shared/config/storybook/themeDecorator/themeDecorator';
import { Theme } from 'app/providers/ThemeProvider';

const preview: Preview = {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
  },
};

export default {
  preview,
  decorators: [styleDecorator, themeDecorator(Theme.LIGHT)],
};
