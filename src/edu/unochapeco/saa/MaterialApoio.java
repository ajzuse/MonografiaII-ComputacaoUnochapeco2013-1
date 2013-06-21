package edu.unochapeco.saa;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MaterialApoio {

    private String session = null;

    public MaterialApoio(String session) {
        this.session = session;
    }

    public String getMateriais() throws IOException {
        String url = "https://www.unochapeco.edu.br/saa/materialApoio.php";

        Document document = Jsoup.connect(url)
                .cookie("PHPSESSID", session)
                .timeout(8000)
                .get();

        Elements disciplinas = document.select("form tr td:eq(1) a");

        JSONArray disciplinaArray = new JSONArray();
        for (Element disciplina : disciplinas) {
            JSONObject disciplinaObject = new JSONObject();
            disciplinaObject.put("nome", disciplina.text());
            disciplinaObject.put("materiais", parse(disciplina.attr("href")));

            disciplinaArray.put(disciplinaObject);
        }
        return new JSONObject().put("disciplinas", disciplinaArray).toString();
    }

    private JSONArray parse(String url) throws IOException {
        String base = "https://www.unochapeco.edu.br";

        Document document = Jsoup.connect(base + url)
                .cookie("PHPSESSID", session)
                .timeout(8000)
                .get();

        Elements arquivos = document.select("form tr:contains(Arquivo) a");
        Elements publicacoes = document
                .select("form tr:contains(Publicação) td:eq(1)");
        Elements descricoes = document
                .select("form tr:contains(Descrição) td:eq(1)");

        JSONArray materialArray = new JSONArray();
        for (int i = 0; i < publicacoes.size(); i++) {
            JSONObject materialObject = new JSONObject();

            materialObject.put("nome", arquivos.get(i + 1).text());
            materialObject.put("url", base + arquivos.get(i + 1).attr("href"));
            materialObject.put("publicacao", publicacoes.get(i).text());
            materialObject.put("descricao", descricoes.get(i).text());

            materialArray.put(materialObject);
        }

        /*
         * Link para fazer o download de todos os arquivos (.zip)
         */
        try {
            Element todos = document.select("form tr a")
                    .select(":contains(.zip)").last();

            materialArray.put(new JSONObject()
                    .put("nome", "Todos os arquivos")
                    .put("url", base + todos.attr("href"))
                    .put("publicacao", "")
                    .put("descricao", todos.text()));
        } catch (NullPointerException e) {
            ;
        }
        return materialArray;
    }
}