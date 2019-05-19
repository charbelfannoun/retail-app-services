package com.retail.store.exceptions;

/**
 * @author Charbel.f
 */
public class NotFoundException extends BaseException {

    /**
     *Constructs an instance of {@link NotFoundException} with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotFoundException(String message)
    {
        super(message);
    }
}
