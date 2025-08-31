declare module '*.module.css' {
	const classes: { [key: string]: string }
	export default classes
}

declare module '*.module.scss' {
	const classes: { [key: string]: string }
	export default classes
}

// import "i18next";
// import translation from "public/locales/en/translation.json";
//
// declare module "i18next" {
// 	interface CustomTypeOptions {
// 		defaultNS: "translation";
// 		resources: {
// 			common: typeof translation;
// 		};
// 	}
// }
