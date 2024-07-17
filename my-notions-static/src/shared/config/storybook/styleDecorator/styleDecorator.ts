import 'app/styles/index.scss';
import { StoryFn } from '@storybook/react'

export const styleDecorator = (story: () => StoryFn) => story();
