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
        textarea.setAttribute("wrap", "soft");
        textarea.getClassList().add("sql-editor-input");
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
