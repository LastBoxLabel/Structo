package tech.lastbox.structo.services.Prompt;

public enum Generate
{
    STRUCTURE {
        public String value(String description) {
            return ("You are a software architect assistant. Given a project description, including the tech stack, required entities and classes, and general application functionality, generate only the project structure in JSON format. " +
                    "Do not include any explanatory messages before or after the JSON output. " +
                    description +
                    "The output should be a JSON representation of the project structure, including folders for components, controllers, models, etc., with the class names specified within each package. For instance, a 'controllers' package might look like: 'controllers': ['UserController', 'ProductController', 'OrderController']." +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }

   }, TASK {
        public String value(String description) {
            return ("You are a software architect assistant. Given a project description and its structure, generate a list of tasks in JSON format. Do not include any explanatory messages before or after the JSON output." +
                    "Project Description:" +
                    description +
                    "The output should be a JSON array of tasks. Each task should have a 'Name', 'Description' and 'ProjectPath'.  'ProjectPath' should indicate where the task should be implemented in the project structure. Be specific and provide context in the descriptions, using relevant keywords related to governmental audits.  " +
                    "For instance: [{'Name': 'Generate Risk Matrix', 'Description': 'Develop a functionality to automatically generate a risk matrix based on audit objectives and using a Generative AI model trained on governmental audit data and risk analysis.', 'ProjectPath': 'src/main/java/com/example/auditplatform/riskmanagement'}, {'Name': 'Analyze Audit Recommendations', 'Description': 'Utilize a Generative AI model to analyze the fulfillment of audit recommendations based on the provided documents and generate a report summarizing the findings.', 'ProjectPath': 'src/main/java/com/example/auditplatform/recommendationanalysis'}]. " +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }
    }, DIAGRAM {
        public String value(String description) {
            return description;
        }
    };

    public abstract String value(String description);

}
