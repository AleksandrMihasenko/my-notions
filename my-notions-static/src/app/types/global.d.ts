declare module '*.scss' {
    interface IClassName {
        [className: string]: string;
    }
    const classNames: IClassName;
    export = classNames;
}

declare module '*.png';
declare module '*.jpg';
declare module '*.jpeg';
declare module "*.svg" {
    const content: React.FunctionComponent<React.SVGAttributes<SVGElement>>;
    export default content;
}

declare const __IS_DEV__: boolean;
