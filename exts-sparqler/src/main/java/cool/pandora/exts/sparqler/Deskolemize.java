/*
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
 */

package cool.pandora.exts.sparqler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deskolemize.
 *
 * @author @christopher-johnson
 */
public class Deskolemize {

    public boolean isWellKnownURI(String v) {
        Pattern p = Pattern.compile("^.+?#genid.+?$");
        Matcher m = p.matcher(v);
        return m.find();
    }

    /**
     * Converts a SkolemIRI to a BNode.
     *
     * @param input the SkolemIRI to convert.
     * @return a BNode.
     */
    static String convertSkolem(String input) {
        Pattern p = Pattern.compile("http://([^>]+)#genid([0-9a-f]{8}-([0-9a-f]{4}-)"
                + "{3}[0-9a-f]{12})");
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer(input.length());
        while (m.find()) {
            String id = m.group(2);
            String bnode = "_:b" + id;
            m.appendReplacement(sb, Matcher.quoteReplacement(bnode));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
