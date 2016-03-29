package org.wso2.carbon.uuf.handlebars.helpers.runtime;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.common.collect.Multimap;
import org.wso2.carbon.uuf.handlebars.HbsRenderable;
import org.wso2.carbon.uuf.core.Fragment;
import org.wso2.carbon.uuf.core.Renderable;
import org.wso2.carbon.uuf.core.UUFException;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class DefineZoneHelper implements Helper<String> {

    public static final String HELPER_NAME = "defineZone";

    @Override
    public CharSequence apply(String zoneName, Options options) throws IOException {
        //TODO: remove duplicate, IncludeFragmentHelper
        Multimap<String, Renderable> bindings = options.data(HbsRenderable.BINDING_KEY);
        Map<String, Fragment> fragments = options.data(HbsRenderable.FRAGMENT_KEY);
        Collection<Renderable> renderables = bindings.get(zoneName);
        if (renderables.isEmpty()) {
            throw new UUFException("Zone '" + zoneName + "' does not have a binding.");
        }
        StringBuilder buffer = new StringBuilder();
        for (Renderable renderable : renderables) {
            String content = renderable.render(options.context, bindings, fragments).trim();
            buffer.append(content);
        }
        return new Handlebars.SafeString(buffer.toString());
    }
}