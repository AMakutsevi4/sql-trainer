package ru.sqltrainer.sql_trainer.ui.trainee;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;

import java.util.concurrent.CompletableFuture;

public class SqlEditor extends Div {

    private final Element textarea = new Element("textarea");
    private String pendingValue = "SELECT\n";
    private boolean scriptsRequested;

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
                """
                        if (this._cm) {
                          this._cm.setValue($0);
                        } else {
                          const ta = this.querySelector('textarea');
                          if (ta) ta.value = $0;
                        }
                        """,
                pendingValue
        );
    }

    public CompletableFuture<String> getValueAsync() {
        return getElement().executeJs(
                        """
                                if (this._cm) {
                                  return this._cm.getValue();
                                }
                                const ta = this.querySelector('textarea');
                                return ta ? ta.value : '';
                                """
                )
                .toCompletableFuture()
                .thenApply(json -> json == null ? "" : json.asString());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        ensureScripts(attachEvent.getUI());
        initEditor();
    }

    private void ensureScripts(UI ui) {
        if (scriptsRequested) {
            return;
        }
        scriptsRequested = true;
        ui.getPage().addStyleSheet(
                "https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.16/codemirror.min.css");
        ui.getPage().addJavaScript(
                "https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.16/codemirror.min.js");
        ui.getPage().addJavaScript(
                "https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.16/mode/sql/sql.min.js");
    }

    private void initEditor() {
        getElement().executeJs(
                """
                        const root = this;
                        const boot = (tries) => {
                          if (typeof CodeMirror === 'undefined') {
                            if (tries < 50) setTimeout(() => boot(tries + 1), 50);
                            return;
                          }
                          if (root._cm) {
                            return;
                          }
                          const ta = root.querySelector('textarea');
                          if (!ta) {
                            if (tries < 50) setTimeout(() => boot(tries + 1), 50);
                            return;
                          }
                          root._cm = CodeMirror.fromTextArea(ta, {
                            mode: 'text/x-sql',
                            lineNumbers: true,
                            lineWrapping: true,
                            viewportMargin: Infinity
                          });
                          root._cm.setSize('100%', '180px');
                          if ($0) {
                            root._cm.setValue($0);
                          }
                          root._cm.refresh();
                        };
                        boot(0);
                        """,
                pendingValue
        );
    }
}
