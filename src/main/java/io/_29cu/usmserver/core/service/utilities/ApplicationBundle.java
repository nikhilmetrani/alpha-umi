package io._29cu.usmserver.core.service.utilities;

/**
 * Created by yniu on 17/10/2016.
 */
import io._29cu.usmserver.core.model.entities.Application;
import java.util.List;

public class ApplicationBundle extends EntityList<Application> {

    public ApplicationBundle() {}

    public ApplicationBundle(List<Application> applications) {
        super();
        super.setItems(applications);
    }

    public List<Application> getApplications() {
        return super.getItems();
    }

    public void setApplications(List<Application> applications) {
        super.setItems(applications);
    }
}
