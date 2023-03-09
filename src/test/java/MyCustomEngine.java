import org.junit.platform.engine.*;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

public class MyCustomEngine implements TestEngine {

    public static final String ENGINE_ID = "my-custome-engine";

    @Override
    public String getId() {
        return ENGINE_ID;
    }

    @Override
    public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
        TestDescriptor testDescriptor= new EngineDescriptor(uniqueId,"My test");
        return testDescriptor;
    }

    @Override
    public void execute(ExecutionRequest request) {

    }
}
