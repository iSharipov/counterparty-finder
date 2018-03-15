package fintech.tinkoff.ru.counterpartyfinder.app;

import timber.log.Timber;

/**
 * 15.03.2018.
 */

public class DebugTree extends Timber.DebugTree{
    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return String.format("[L:%s] [M:%s] [C:%s]",
                element.getLineNumber(),
                element.getMethodName(),
                super.createStackElementTag(element));
    }
}
