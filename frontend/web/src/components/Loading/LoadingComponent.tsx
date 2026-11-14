interface LoadingComponentProps {
    title?: string;
    description?: string;
}

export default function LoadingComponent({title, description}: LoadingComponentProps) {
    return (
        <>
            <div className="min-h-[60vh] flex items-center justify-center p-6" role="status">
                <div className="flex flex-col items-center gap-4 text-center">
                    <div className="w-12 h-12 border-4 border-[#D5E1EE] border-t-[#4A90D9] rounded-full animate-spin" />

                    <div className="space-y-1">
                        <h3 className="text-xl font-bold text-[#1E3A5F]">
                            {title || "Loading..."}
                        </h3>
                        <p className="text-[#4A90C2]">
                            {description || "Please wait while we fetch the data."}
                        </p>
                    </div>
                </div>
            </div>
        </>
    )
}