import { ReactNode } from 'react';
import * as DialogPrimitive from '@radix-ui/react-dialog';

interface DialogProps {
    open?: boolean;
    onOpenChange?: (open: boolean) => void;
    children: ReactNode;
}

export function Dialog({ open, onOpenChange, children }: DialogProps) {
    return (
        <DialogPrimitive.Root open={open} onOpenChange={onOpenChange}>
            {children}
        </DialogPrimitive.Root>
    );
}

export function DialogContent({
    title,
    children,
}: {
    title: string;
    children: ReactNode;
}) {
    return (
        <DialogPrimitive.Portal>
            <DialogPrimitive.Overlay className="fixed inset-0 bg-black/50 z-40" />
            <div className="fixed inset-0 z-50 flex items-center justify-center">
                <DialogPrimitive.Content
                    className="bg-white rounded-lg shadow-lg p-6 w-full max-w-md"
                    aria-labelledby="dialog-title"
                >
                    <DialogPrimitive.Title
                        id="dialog-title"
                        className="sr-only"
                    >
                        {title}
                    </DialogPrimitive.Title>
                    {children}
                </DialogPrimitive.Content>
            </div>
        </DialogPrimitive.Portal>
    );
}

export function DialogHeader({ children }: { children: ReactNode }) {
    return <div className="mb-4">{children}</div>;
}

export function DialogTitle({ children }: { children: ReactNode }) {
    return <h2 className="text-xl font-bold">{children}</h2>;
}

export function DialogFooter({ children }: { children: ReactNode }) {
    return <div className="mt-4 flex justify-end space-x-2">{children}</div>;
}
