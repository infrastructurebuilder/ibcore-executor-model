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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.infrastructurebuilder.model.validation.utils.ExtendedSchemaClient;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.erosb.jsonsKema.ClassPathAwareSchemaClient;
import com.github.erosb.jsonsKema.FormatValidationPolicy;
import com.github.erosb.jsonsKema.JsonParser;
import com.github.erosb.jsonsKema.JsonValue;
import com.github.erosb.jsonsKema.MemoizingSchemaClient;
import com.github.erosb.jsonsKema.PrepopulatedSchemaClient;
import com.github.erosb.jsonsKema.Schema;
import com.github.erosb.jsonsKema.SchemaClient;
import com.github.erosb.jsonsKema.SchemaLoader;
import com.github.erosb.jsonsKema.SchemaLoaderConfig;
import com.github.erosb.jsonsKema.URLQueryingSchemaClient;
import com.github.erosb.jsonsKema.ValidationFailure;
import com.github.erosb.jsonsKema.Validator;
import com.github.erosb.jsonsKema.ValidatorConfig;

class IBCoreExecutorModelUtilsTest {

  private static final String G_P_E = "generated-process-execution";

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  @BeforeEach
  void setUp() throws Exception {
  }

  @Test
  void test() {
    assertNotNull(new IBCoreExecutorModelUtils());
    var q = IBCoreExecutorModelUtils.getObjectMapper();
    assertNotNull(q);
    assertEquals(q, IBCoreExecutorModelUtils.getObjectMapper());
  }

  @Test
  void test2() throws IOException, URISyntaxException {
    ExecutorModelSchemaClientProducer emp = new ExecutorModelSchemaClientProducer();
    ExtendedSchemaClient sc = emp.getSchemaClient().get();
    URI u = sc.getURI(G_P_E).get();
    String rawString = """
        {
                "modelVersion":"0.0"
                , "id" : "id"
                , "executable" : "exec"
                , "root" : "pathref:key:abc:/"
        }
                """;
    Validator validator = emp.getValidatorFor(G_P_E).get();
    JsonValue json = new JsonParser(rawString, u).parse();
    ValidationFailure validation = validator.validate(json);
    assertNotNull(validation);
    List<ValidationFailure> flatten = validation.flatten();
    assertTrue(flatten.size() > 0);
    Validator validator2 = emp.getValidatorFor(G_P_E).get();
    String rawString2 = """
        {
                "modelVersion":"0.0"
                , "id" : "id"
                , "executable" : "exec"
                , "arguments" : [ "A" ]
                , "root" : "pathref:key:abc:/"
        }
                """;
    JsonValue json2 = new JsonParser(rawString2, u).parse();
    ValidationFailure v2 = validator.validate(json2);
    assertNull(v2);

    // Need to validate a final model somehow?
  }

}
