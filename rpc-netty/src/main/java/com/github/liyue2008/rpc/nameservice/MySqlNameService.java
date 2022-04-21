/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liyue2008.rpc.nameservice;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.liyue2008.rpc.NameService;
import com.github.liyue2008.rpc.util.DBUtil;

public class MySqlNameService implements NameService {

    private static final Logger logger = LoggerFactory.getLogger(MySqlNameService.class);
    private static final Collection<String> schemes = Collections.singleton("mysql");

    @Override
    public Collection<String> supportedSchemes() {
        return schemes;
    }

    @Override
    public void connect(URI nameServiceUri) {
        if (schemes.contains(nameServiceUri.getScheme())) {
            DBUtil.getConnection();
        } else {
            throw new RuntimeException("Unsupported scheme!");
        }
    }

    @Override
    public synchronized void registerService(String serviceName, URI uri) throws IOException {
        logger.info("Register service: {}, uri: {}.", serviceName, uri);
        List<URI> uriList = DBUtil.select("SELECT `host`,`port` FROM `t_service_name` WHERE `service_name` = " +
                "'" + serviceName + "'");
        if (uriList == null || uriList.isEmpty()) {
            DBUtil.update("INSERT INTO `t_service_name`(`service_name`,`host`,`port`) VALUES(?, ?, ?);",
                    new Object[] {serviceName, uri.getHost(), uri.getPort()});
        }
    }

    @Override
    public URI lookupService(String serviceName) throws IOException {
        List<URI> uriList = DBUtil.select("SELECT `host`,`port` FROM `t_service_name` WHERE `service_name` = " +
                "'" + serviceName + "'");
        if (null == uriList || uriList.isEmpty()) {
            return null;
        } else {
            return uriList.get(ThreadLocalRandom.current().nextInt(uriList.size()));
        }
    }

}
