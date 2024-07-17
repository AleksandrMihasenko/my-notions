import type { Meta, StoryObj } from '@storybook/react';
import { fn } from '@storybook/test';
import { Button, ButtonTheme } from './Button';
import { themeDecorator } from 'shared/config/storybook/themeDecorator/themeDecorator';
import { Theme } from 'app/providers/ThemeProvider';

const meta = {
    title: 'shared/Button',
    component: Button,
    argTypes: {
        theme: { control: 'color' },
    },
    args: { onClick: fn() },
} satisfies Meta<typeof Button>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
    args: {
        children: 'Text',
    },
};

export const Clear: Story = {
    args: {
        children: 'Text',
        theme: ButtonTheme.CLEAR,
    },
};

export const Outline: Story = {
    args: {
        children: 'Text',
        theme: ButtonTheme.OUTLINE,
    },
};

export const PrimaryDark: Story = {
    args: {
        children: 'Text',
    },
};
PrimaryDark.decorators = [themeDecorator(Theme.DARK)]

export const ClearDark: Story = {
    args: {
        children: 'Text',
        theme: ButtonTheme.CLEAR,
    },
};
ClearDark.decorators = [themeDecorator(Theme.DARK)]

export const OutlineDark: Story = {
    args: {
        children: 'Text',
        theme: ButtonTheme.OUTLINE,
    },
};
OutlineDark.decorators = [themeDecorator(Theme.DARK)]
