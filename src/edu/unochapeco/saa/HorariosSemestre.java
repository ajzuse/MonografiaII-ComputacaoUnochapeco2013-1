package edu.unochapeco.saa;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HorariosSemestre {

    private String session = null;

    public HorariosSemestre(String session) {
        this.session = session;
    }

    public String getHorarios() throws IOException {
        String url = "https://www.unochapeco.edu.br/saa/hor_aluno.php";

        Document document = Jsoup.connect(url)
                .cookie("PHPSESSID", session)
                .timeout(8000)
                .get();

        Elements codigos = document.select("form tr:gt(1) td:eq(0) a");
        Elements nomes = document.select("form tr:gt(1) td:eq(1) a");
        Elements turmas = document.select("form tr:gt(1) td:eq(2) a");

        JSONArray disciplinasArray = new JSONArray();
        for (int i = 0; i < codigos.size(); i++) {
            JSONObject disciplinaObject = new JSONObject();
            disciplinaObject.put("codigo", codigos.get(i).text());
            disciplinaObject.put("nome", nomes.get(i).text());
            disciplinaObject.put("turma", turmas.get(i).text());
            disciplinaObject.put("dados", parse(nomes.get(i).attr("href")));

            disciplinasArray.put(disciplinaObject);
        }
        return new JSONObject().put("disciplinas", disciplinasArray).toString();
    }

    public JSONObject parse(String url) throws IOException {
        String base = "https://www.unochapeco.edu.br/saa/";

        Document document = Jsoup.connect(base + url)
                .cookie("PHPSESSID", session)
                .timeout(8000)
                .get();

        String[] detalhes = {"curso", "grade", "disciplina", "período",
            "professor", "turno", "créditos", "g2", "g3"};

        JSONObject detalhesObject = new JSONObject();
        try {
            for (String detalhe : detalhes) {
                Element element = document
                        .select("form tr[bgcolor]:contains(" + detalhe
                        + ") td:eq(1)").first();

                detalhesObject.put(detalhe, element.text());
            }
        } catch (NullPointerException e) {
            ;
        }

        JSONArray horariosArray = new JSONArray();
        try {
            Elements horarios = document.select("form table:eq(1) tr:gt(1)");
            for (Element horario : horarios) {
                JSONObject horarioObject = new JSONObject();

                horarioObject.put("dia", horario.select("td:eq(0)").text());
                horarioObject.put("semana", horario.select("td:eq(1)").text());
                horarioObject.put("hora", horario.select("td:eq(2)").text());

                if (!horario.select("b").isEmpty()) {
                    horarioObject.put("ocorreu", "false");
                } else {
                    horarioObject.put("ocorreu", "true");
                }

                horariosArray.put(horarioObject);
            }
        } catch (NullPointerException e) {
            ;
        }
        return new JSONObject()
                .put("detalhes", detalhesObject)
                .put("horarios", horariosArray);
    }
}