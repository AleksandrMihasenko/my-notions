import translation from 'public/locales/en/translation.json';

declare module 'i18next' {
	interface CustomTypeOptions {
		defaultNS: 'en',
		resources: typeof translation['en']
	}
}
