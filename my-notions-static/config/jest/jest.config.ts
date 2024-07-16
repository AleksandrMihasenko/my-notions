import type { Config } from 'jest';
import path from 'path';

const config: Config = {
  clearMocks: true,
  coveragePathIgnorePatterns: [
    '/node_modules/'
  ],
  moduleDirectories: [
    'node_modules'
  ],
  moduleFileExtensions: [
    'js',
    'mjs',
    'cjs',
    'jsx',
    'ts',
    'tsx',
    'json',
    'node'
  ],
  rootDir: '../../',
  testEnvironment: 'jsdom',
  testMatch: [
    '<rootDir>src/**/*(*.)@(spec|test).[tj]s?(x)'
  ],
  modulePaths: [
      '<rootDir>/src'
  ],
  setupFilesAfterEnv: ['<rootDir>/config/jest/setupTests.ts'],
  moduleNameMapper: {
    '\\.(jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2|mp4|webm|wav|mp3|m4a|aac|oga)$': path.resolve(__dirname, 'jestEmptyComponent.tsx'),
    '\\.(css|scss|sass|less)$': 'identity-obj-proxy',
  }
};

export default config;
