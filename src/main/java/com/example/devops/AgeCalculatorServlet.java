package com.example.devops;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/calculate-age")
public class AgeCalculatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirige vers la page d'accueil
        response.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Récupérer les paramètres
            String name = request.getParameter("name");
            String birthYearStr = request.getParameter("birthYear");
            String birthMonthStr = request.getParameter("birthMonth");
            String birthDayStr = request.getParameter("birthDay");

            // Page HTML de base
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Résultat - Calculateur d'Âge</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 40px; background: #f0f0f0; }");
            out.println(".container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
            out.println(".result { background: #e8f5e9; padding: 15px; border-left: 5px solid #4CAF50; margin: 20px 0; }");
            out.println(".error { background: #ffebee; padding: 15px; border-left: 5px solid #f44336; }");
            out.println("button { background: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>🎂 Calculateur d'Âge DevOps</h1>");

            // Vérifier si tous les paramètres sont présents
            if (birthYearStr == null || birthMonthStr == null || birthDayStr == null ||
                    birthYearStr.isEmpty() || birthMonthStr.isEmpty() || birthDayStr.isEmpty()) {

                out.println("<div class='error'>");
                out.println("<h3>❌ Erreur</h3>");
                out.println("<p>Veuillez remplir tous les champs!</p>");
                out.println("</div>");

            } else {
                // Convertir les valeurs
                int birthYear = Integer.parseInt(birthYearStr);
                int birthMonth = Integer.parseInt(birthMonthStr);
                int birthDay = Integer.parseInt(birthDayStr);

                // Date actuelle
                LocalDate currentDate = LocalDate.now();
                // Date de naissance
                LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

                // Vérifier si la date est valide
                if (birthDate.isAfter(currentDate)) {
                    out.println("<div class='error'>");
                    out.println("<h3>❌ Date invalide</h3>");
                    out.println("<p>La date de naissance ne peut pas être dans le futur!</p>");
                    out.println("</div>");
                } else {
                    // Calculer l'âge
                    Period age = Period.between(birthDate, currentDate);

                    // Afficher le résultat
                    out.println("<div class='result'>");
                    out.println("<h2>📊 Résultat</h2>");
                    if (name != null && !name.trim().isEmpty()) {
                        out.println("<p><strong>Nom:</strong> " + name + "</p>");
                    }
                    out.println("<p><strong>Date de naissance:</strong> " +
                            birthDay + "/" + birthMonth + "/" + birthYear + "</p>");
                    out.println("<p><strong>Âge exact:</strong></p>");
                    out.println("<ul>");
                    out.println("<li>" + age.getYears() + " ans</li>");
                    out.println("<li>" + age.getMonths() + " mois</li>");
                    out.println("<li>" + age.getDays() + " jours</li>");
                    out.println("</ul>");

                    // Message spécial selon l'âge
                    if (age.getYears() < 1) {
                        out.println("<p>👶 Tu es un bébé!</p>");
                    } else if (age.getYears() < 18) {
                        out.println("<p>🎮 Tu es mineur(e)</p>");
                    } else if (age.getYears() >= 65) {
                        out.println("<p>👴🏽👵🏽 Tu es à la retraite!</p>");
                    } else {
                        out.println("<p>💼 Tu es majeur(e)</p>");
                    }
                    out.println("</div>");

                    // Statistiques DevOps (simulées)
                    out.println("<div style='margin-top: 20px; padding: 10px; background: #e3f2fd;'>");
                    out.println("<h3>📈 Infos DevOps</h3>");
                    out.println("<p><strong>Version de l'application:</strong> 1.0.0</p>");
                    out.println("<p><strong>Dernier déploiement:</strong> " + currentDate + "</p>");
                    out.println("<p><strong>Environnement:</strong> " +
                            (System.getenv("APP_ENV") != null ? System.getenv("APP_ENV") : "development") + "</p>");
                    out.println("</div>");
                }
            }

            // Bouton pour recommencer
            out.println("<br>");
            out.println("<form action='index.html'>");
            out.println("<button type='submit'>🔁 Faire un autre calcul</button>");
            out.println("</form>");

            out.println("</div>"); // Fermeture container
            out.println("</body>");
            out.println("</html>");

        } catch (NumberFormatException e) {
            out.println("<div class='error'>");
            out.println("<h3>❌ Erreur de format</h3>");
            out.println("<p>Veuillez entrer des nombres valides!</p>");
            out.println("</div>");
        } finally {
            out.close();
        }
    }
}