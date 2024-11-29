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
            return ("You are a software architect assistant. Given a project description and its structure, generate a list of diagrams in JSON format. Each diagram should be represented in Mermaid syntax. Do not include any explanatory messages before or after the JSON output." +
                    "Project Description: " + description +
                    "The output should be a JSON array of diagrams. Each diagram should have a 'Name', 'Type', 'Description', 'Mermaid Code' and 'ProjectPath'.  'Type' can be  'UseCase Diagram', 'Class Diagram', 'Sequence Diagram', or 'Data Flow Diagram'. 'ProjectPath' should indicate where the diagram should be implemented in the project structure. 'Mermaid Code' should contain the Mermaid code for the diagram. Be specific and provide context in the descriptions, using relevant keywords related to governmental audits." +
                    "For instance: [{'Name': 'Risk Management', 'Type': 'UseCase Diagram', 'Description': 'A use case diagram illustrating the interactions between auditors and the system to manage and analyze audit risks.', 'Mermaid Code': '```mermaid\ngraph LR\nAuditor --> (Manage Risks)\nAuditor --> (Analyze Risks)\n(Manage Risks) --> System\n(Analyze Risks) --> System\n```', 'ProjectPath': 'documentation/diagrams/risks'}, {'Name': 'Audit Recommendation Analysis', 'Type': 'Sequence Diagram', 'Description': 'A sequence diagram detailing the process of analyzing audit recommendations using a Generative AI model, including the data input, processing, and report generation steps.', 'Mermaid Code': '```mermaid\nsequenceDiagram\nAuditor->>+AI Model: Input Audit Data\nAI Model->>-Auditor: Generate Analysis Report\n```', 'ProjectPath': 'documentation/diagrams/recommendations'}]. " +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }
    };

    public abstract String value(String description);

}
