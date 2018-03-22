package fintech.tinkoff.ru.counterpartyfinder.data.network;

import java.io.IOException;
import java.net.URI;

import fintech.tinkoff.ru.counterpartyfinder.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 21.03.2018.
 */

public class FakeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        String responseString = "";
        if (BuildConfig.DEBUG) {
            responseString = "{\n" +
                    "  \"body\": {\n" +
                    "    \"suggestions\": [\n" +
                    "      {\n" +
                    "        \"value\": \"ООО \\\"МОТОРИКА\\\"\",\n" +
                    "        \"unrestricted_value\": \"ООО \\\"МОТОРИКА\\\"\",\n" +
                    "        \"data\": {\n" +
                    "          \"kpp\": \"775101001\",\n" +
                    "          \"capital\": null,\n" +
                    "          \"management\": {\n" +
                    "            \"name\": \"Чех Илья Игоревич\",\n" +
                    "            \"post\": \"Генеральный директор\"\n" +
                    "          },\n" +
                    "          \"branch_type\": \"MAIN\",\n" +
                    "          \"branch_count\": 0,\n" +
                    "          \"source\": null,\n" +
                    "          \"qc\": null,\n" +
                    "          \"hid\": \"a498ee35e0b125380cff43434ae9c2f1ac3742c6636ceb327afaaa54d247e7cc\",\n" +
                    "          \"type\": \"LEGAL\",\n" +
                    "          \"state\": {\n" +
                    "            \"status\": \"ACTIVE\",\n" +
                    "            \"actuality_date\": 1488326400000,\n" +
                    "            \"registration_date\": 1423094400000,\n" +
                    "            \"liquidation_date\": null\n" +
                    "          },\n" +
                    "          \"opf\": {\n" +
                    "            \"type\": null,\n" +
                    "            \"code\": \"12300\",\n" +
                    "            \"full\": \"Общество с ограниченной ответственностью\",\n" +
                    "            \"short\": \"ООО\"\n" +
                    "          },\n" +
                    "          \"name\": {\n" +
                    "            \"full_with_opf\": \"ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \\\"МОТОРИКА\\\"\",\n" +
                    "            \"short_with_opf\": \"ООО \\\"МОТОРИКА\\\"\",\n" +
                    "            \"latin\": null,\n" +
                    "            \"full\": \"МОТОРИКА\",\n" +
                    "            \"short\": \"МОТОРИКА\"\n" +
                    "          },\n" +
                    "          \"inn\": \"7719402047\",\n" +
                    "          \"ogrn\": \"1157746078984\",\n" +
                    "          \"okpo\": null,\n" +
                    "          \"okved\": \"72.19\",\n" +
                    "          \"okveds\": null,\n" +
                    "          \"authorities\": null,\n" +
                    "          \"documents\": null,\n" +
                    "          \"licenses\": null,\n" +
                    "          \"address\": {\n" +
                    "            \"value\": \"г Москва, г Троицк, Сиреневый б-р, д 1\",\n" +
                    "            \"unrestricted_value\": \"г Москва, г Троицк, Троицкий округ, Сиреневый б-р, д 1\",\n" +
                    "            \"data\": {\n" +
                    "            }\n" +
                    "          },\n" +
                    "          \"phones\": null,\n" +
                    "          \"emails\": null,\n" +
                    "          \"ogrn_date\": 1423094400000,\n" +
                    "          \"okved_type\": \"2014\"\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";
        }
        response = new Response.Builder()
                .code(200)
                .request(chain.request())
                .message(responseString)
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString))
                .build();
        return response;
    }
}
