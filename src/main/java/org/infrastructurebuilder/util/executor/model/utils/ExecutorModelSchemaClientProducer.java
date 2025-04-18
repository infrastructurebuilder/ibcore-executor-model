/*
 * @formatter:off
 * Copyright © 2019 admin (admin@infrastructurebuilder.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @formatter:on
 */
package org.infrastructurebuilder.util.executor.model.utils;

import static java.util.Objects.requireNonNull;
import static org.infrastructurebuilder.exceptions.IBException.cet;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.infrastructurebuilder.model.validation.utils.AbstractIBModelSchemaValidatorProducer;
import org.infrastructurebuilder.model.validation.utils.ExtendedSchemaClient;
import org.infrastructurebuilder.util.executor.model.IbcoreExecutorModelVersioning;
import org.infrastructurebuilder.util.versions.IBVersionsSupplier;

import com.github.erosb.jsonsKema.MemoizingSchemaClient;
import com.github.erosb.jsonsKema.PrepopulatedSchemaClient;
import com.github.erosb.jsonsKema.SchemaClient;
import com.github.erosb.jsonsKema.URLQueryingSchemaClient;

public final class ExecutorModelSchemaClientProducer extends AbstractIBModelSchemaValidatorProducer {

  public ExecutorModelSchemaClientProducer() {
  }

  @Override
  protected ExtendedSchemaClient _rawSchemaClient(SchemaClient sub, Map<URI, String> refs) {
    return cet.returns(() -> new ExtendedSchemaClientImpl(sub, refs));
  }

  private final static class ExtendedSchemaClientImpl extends AbstractExtendedClient {
    private final static Set<String> list = Set.of(//
        "environment" //
        , "generated-process-execution" //
        , "generated-process-execution-result" //
    );
    private final static String urifmt = "https://infrastructurebuilder.org/util/executor/model/v%s/%s.schema.json";
    private final static String cpfmt = "jsonschema/v%s/%s.schema.json"; // TODO Figure this out

    private ExtendedSchemaClientImpl(SchemaClient delegate, Map<URI, String> refs) {
      super(delegate, refs);
    }

    public Set<String> getRefNamesSet() {
      return list;
    }

    @Override
    public IBVersionsSupplier getVersion() {
      return new IbcoreExecutorModelVersioning();
    }

    @Override
    public String getURIFormatString() {
      return urifmt;
    }

    @Override
    public String getClasspathFormatString() {
      return cpfmt;
    }

  }
}
