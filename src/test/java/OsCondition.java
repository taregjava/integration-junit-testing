import com.halfacode.exception.Os;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

public class OsCondition implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {

        Optional<AnnotatedElement> element= context.getElement();
        ConditionEvaluationResult out= ConditionEvaluationResult.enabled("@DisabledOnOs is not present");
        Optional<DisabledOnOs> disabledOnOs= AnnotationUtils.findAnnotation(element,DisabledOnOs.class);
        if (disabledOnOs.isPresent()){
            Os myOs= Os.determine();
        }
        return out;
    }
}
