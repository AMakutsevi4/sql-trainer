package ru.sqltrainer.sql_trainer.ui.trainee;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;

import java.util.concurrent.CompletableFuture;

/**
 * Простой SQL-редактор без CDN.
 * На работе сеть часто режет внешние скрипты — поэтому без CodeMirror.
 */
public class SqlEditor extends Div {

    private final Element textarea = new Element("textarea");
    private String pendingValue = "SELECT\n";

    public SqlEditor() {
        addClassName("sql-editor");
        setWidthFull();

        textarea.setAttribute("spellcheck", "false");
        textarea.getStyle()
                .set("width", "100%")
                .set("height", "180px")
                .set("box-sizing", "border-box")
                .set("resize", "vertical")
                .set("font-family", "var(--mono)")
                .set("font-size", "0.9rem")
                .set("padding", "0.75rem")
                .set("border", "1px solid var(--line)")
                .set("border-radius", "10px")
                .set("background", "var(--soft-box)")
                .set("color", "var(--ink)");
        textarea.setProperty("value", pendingValue);

        getElement().appendChild(textarea);
    }

    public void setValue(String sql) {
        pendingValue = sql == null ? "" : sql;
        textarea.setProperty("value", pendingValue);
        getElement().executeJs(
                "const ta = this.querySelector('textarea'); if (ta) ta.value = $0;",
                pendingValue
        );
    }

    public CompletableFuture<String> getValueAsync() {
        return getElement().executeJs(
                        "const ta = this.querySelector('textarea'); return ta ? ta.value : '';"
                )
                .toCompletableFuture()
                .thenApply(json -> json == null ? "" : json.asString());
    }
}
