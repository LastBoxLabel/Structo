import { ReactNode } from 'react';

interface LabelProps {
    htmlFor: string;
    children: ReactNode;
}

export function Label({ htmlFor, children }: LabelProps) {
    return (
        <label
            htmlFor={htmlFor}
            className="block text-sm font-medium text-gray-700 mb-1"
        >
            {children}
        </label>
    );
}