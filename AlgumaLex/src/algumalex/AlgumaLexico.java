/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algumalex;

/**
 *
 * @author Gilson Prado Jr
 */
public class AlgumaLexico {
    LeitorDeArquivosTexto ldat;
    public AlgumaLexico(String arquivo) {
        ldat = new LeitorDeArquivosTexto(arquivo);
    }
    public Token proximoToken() {
        Token proximo = null;
        espacosEComentarios();
        ldat.confirmar();
        proximo = fim();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = palavrasChave();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = variavel();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = numeros();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = operadorAritmetico();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = operadorRelacional();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = delimitador();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = parenteses();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = cadeia();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        System.err.println("Erro léxico!");
        System.err.println(ldat.toString());
        return null;
    }
    private Token operadorAritmetico() {
        int caractereLido = ldat.lerProximoCaractere();
        char c = (char) caractereLido;
        return switch (c) {
            case '*' -> new Token(TipoToken.OpAritMult, ldat.getLexema());
            case '/' -> new Token(TipoToken.OpAritDiv, ldat.getLexema());
            case '+' -> new Token(TipoToken.OpAritSoma, ldat.getLexema());
            case '-' -> new Token(TipoToken.OpAritSub, ldat.getLexema());
            default -> null;
        };
    }
    private Token delimitador() {
        int caractereLido = ldat.lerProximoCaractere();
        char c = (char) caractereLido;
        if (c == ':') {
            return new Token(TipoToken.Delim, ldat.getLexema());
        } else {
            return null;
        }
    }

    private Token parenteses() {
        int caractereLido = ldat.lerProximoCaractere();
        char c = (char) caractereLido;
        return switch (c) {
            case '(' -> new Token(TipoToken.AbrePar, ldat.getLexema());
            case ')' -> new Token(TipoToken.FechaPar, ldat.getLexema());
            default -> null;
        };
    }
    private Token operadorRelacional() {
        int caractereLido = ldat.lerProximoCaractere();
        char c = (char) caractereLido;
        switch (c) {
            case '<' -> {
                c = (char) ldat.lerProximoCaractere();
                switch (c) {
                    case '>' -> {
                        return new Token(TipoToken.OpRelDif, ldat.getLexema());
                    }
                    case '=' -> {
                        return new Token(TipoToken.OpRelMenorIgual, ldat.getLexema());
                    }
                    default -> {
                        ldat.retroceder();
                        return new Token(TipoToken.OpRelMenor, ldat.getLexema());
                    }
                }
            }
            case '=' -> {
                return new Token(TipoToken.OpRelIgual, ldat.getLexema());
            }
            case '>' -> {
                c = (char) ldat.lerProximoCaractere();
                if (c == '=') {
                    return new Token(TipoToken.OpRelMaiorIgual, ldat.getLexema());
                } else {
                    ldat.retroceder();
                    return new Token(TipoToken.OpRelMaior, ldat.getLexema());
                }
            }
            default -> {
            }
        }
        return null;
    }
    private Token numeros() {
        int estado = 1;
        while (true) {
            char c = (char) ldat.lerProximoCaractere();
            switch (estado) {
                case 1 -> {
                    if (Character.isDigit(c)) {
                        estado = 2;
                    } else {
                        return null;
                    }
                }
                case 2 -> {
                    if (c == '.') {
                        c = (char) ldat.lerProximoCaractere();
                        if (Character.isDigit(c)) {
                            estado = 3;
                        } else {
                            return null;
                        }
                    } else if (!Character.isDigit(c)) {
                        ldat.retroceder();
                        return new Token(TipoToken.NumInt, ldat.getLexema());
                    }
                }
                case 3 -> {
                    if (!Character.isDigit(c)) {
                        ldat.retroceder();
                        return new Token(TipoToken.NumReal, ldat.getLexema());
                    }
                }
                default -> {
                }
            }
        }
    }
    private Token variavel() {
        int estado = 1;
        while (true) {
            char c = (char) ldat.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isLetter(c)) {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (!Character.isLetterOrDigit(c)) {
                    ldat.retroceder();
                    return new Token(TipoToken.Var, ldat.getLexema());
                }
            }
        }
    }
    private Token cadeia() {
        int estado = 1;
        while (true) {
            char c = (char) ldat.lerProximoCaractere();
            switch (estado) {
                case 1 -> {
                    if (c == '\'') {
                        estado = 2;
                    } else {
                        return null;
                    }
                }
                case 2 -> {
                    if (c == '\n') {
                        return null;
                    }   if (c == '\'') {
                        return new Token(TipoToken.Cadeia, ldat.getLexema());
                    } else if (c == '\\') {
                        estado = 3;
                    }
                }
                case 3 -> {
                    if (c == '\n') {
                        return null;
                    } else {
                        estado = 2;
                    }
                }
                default -> {
                }
            }
        }
    }
    private void espacosEComentarios() {
        int estado = 1;
        while (true) {
            char c = (char) ldat.lerProximoCaractere();
            switch (estado) {
                case 1 -> {
                    if (Character.isWhitespace(c) || c == ' ') {
                        estado = 2;
                    } else if (c == '%') {
                        estado = 3;
                    } else {
                        ldat.retroceder();
                        return;
                    }
                }
                case 2 -> {
                    if (c == '%') {
                        estado = 3;
                    } else if (!(Character.isWhitespace(c) || c == ' ')) {
                        ldat.retroceder();
                        return;
                    }
                }
                case 3 -> {
                    if (c == '\n') {
                        return;
                    }
                }
                default -> {
                }
            }
        }
    }
    private Token palavrasChave() {
        while (true) {
            char c = (char) ldat.lerProximoCaractere();
            if (!Character.isLetter(c)) {
                ldat.retroceder();
                String lexema = ldat.getLexema();
                return switch (lexema) {
                    case "DECLARACOES" -> new Token(TipoToken.PCDeclaracoes, lexema);
                    case "ALGORITMO" -> new Token(TipoToken.PCAlgoritmo, lexema);
                    case "INTEIRO" -> new Token(TipoToken.PCInteiro, lexema);
                    case "REAL" -> new Token(TipoToken.PCReal, lexema);
                    case "ATRIBUIR" -> new Token(TipoToken.PCAtribuir, lexema);
                    case "A" -> new Token(TipoToken.PCA, lexema);
                    case "LER" -> new Token(TipoToken.PCLer, lexema);
                    case "IMPRIMIR" -> new Token(TipoToken.PCImprimir, lexema);
                    case "SE" -> new Token(TipoToken.PCSe, lexema);
                    case "ENTAO" -> new Token(TipoToken.PCEntao, lexema);
                    case "ENQUANTO" -> new Token(TipoToken.PCEnquanto, lexema);
                    case "INICIO" -> new Token(TipoToken.PCInicio, lexema);
                    case "FIM" -> new Token(TipoToken.PCFim, lexema);
                    case "E" -> new Token(TipoToken.OpBoolE, lexema);
                    case "OU" -> new Token(TipoToken.OpBoolOu, lexema);
                    default -> null;
                };
            }
        }
    }
    private Token fim() {
        int caractereLido = ldat.lerProximoCaractere();
        if (caractereLido == -1) {
            return new Token(TipoToken.Fim, "Fim");
        }
        return null;
    }
}