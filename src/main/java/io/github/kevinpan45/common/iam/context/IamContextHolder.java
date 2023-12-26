package io.github.kevinpan45.common.iam.context;

/**
 * ThreadLocal holder for the current {@link IamContext}.
 */
public class IamContextHolder {
    
    private static final ThreadLocal<IamContext> CONTEXT = new ThreadLocal<>();

    /**
     * Clears the current context.
     */
    public static void clearContext() {
        CONTEXT.remove();
    }

    /**
     * Obtains the current context.
     * @return a context (never {@code null} - create a default implementation if
     * necessary)
     */
    public static IamContext getContext() {
        IamContext ctx = CONTEXT.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            CONTEXT.set(ctx);
        }
        return ctx;
    }

    /**
     * Sets the current context.
     * @param context to the new argument (should never be {@code null}, although
     * implementations must check if {@code null} has been passed and throw an
     * {@link IllegalArgumentException} in such cases)
     */
    public static void setContext(IamContext context) {
        if (context == null) {
            throw new IllegalArgumentException("Only non-null IamContext instances are permitted");
        }
        CONTEXT.set(context);
    }

    /**
     * Creates a new, empty context implementation, for use by
     * {@link #setContext(IamContext)} when establishing a new security context
     * in the {@link SecurityContextHolder}.
     * <p>
     * The default implementation returns a new {@link IamContext} instance.
     * @return the empty context.
     */
    protected static IamContext createEmptyContext() {
        return new IamContext();
    }
}
