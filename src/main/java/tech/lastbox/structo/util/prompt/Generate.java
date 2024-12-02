package tech.lastbox.structo.util.prompt;

public enum Generate
{
    STRUCTURE {
        @Override
        public String generate(String description, String history, String baseInfo) {
            return ("You are a software architect assistant. Given a project description, including the tech stack, required entities and classes, and general application functionality, generate only the project structure in JSON format. " +
                    "Do not include any explanatory messages before or after the JSON output. " +
                    description +
                    "The output should be a JSON representation of the project structure, including folders for components, controllers, models, etc., with the class names specified within each package. For instance, a 'controllers' package might look like: 'controllers': ['UserController', 'ProductController', 'OrderController']." +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }

   }, TASK {
        @Override
        public String generate(String description, String history, String baseInfo) {
            return ("You are a software architect assistant. Given a project description and its structure, generate a list of tasks in JSON format. Do not include any explanatory messages before or after the JSON output." +
                    "Project Description:" +
                    description +
                    "The output should be a JSON array of tasks. Each task should have a 'Name', 'Description' and 'ProjectPath'.  'ProjectPath' should indicate where the task should be implemented in the project structure. Be specific and provide context in the descriptions, using relevant keywords related to governmental audits.  " +
                    "For instance: [{'Name': 'Generate Risk Matrix', 'Description': 'Develop a functionality to automatically generate a risk matrix based on audit objectives and using a Generative AI model trained on governmental audit data and risk analysis.', 'ProjectPath': 'src/main/java/com/example/auditplatform/riskmanagement'}, {'Name': 'Analyze Audit Recommendations', 'Description': 'Utilize a Generative AI model to analyze the fulfillment of audit recommendations based on the provided documents and generate a report summarizing the findings.', 'ProjectPath': 'src/main/java/com/example/auditplatform/recommendationanalysis'}]. " +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }
    }, DIAGRAM {
        @Override
        public String generate(String description, String history, String baseInfo) {
            return (String.format("""You are a software architect assistant. Given a project description and its structure, generate a list of diagrams in JSON format. Each diagram should be represented in Mermaid syntax. Do not include any explanatory messages before or after the JSON output.
                        Project Description: %s
                        The output should be a JSON array of diagrams. Each diagram should have the following keys: 'Name', 'Type', 'Description', 'Mermaid Code', and 'ProjectPath'.
                        
                        - 'Type' can be one of the following:
                          - Use Case Diagram: Represents the functional requirements of the system, showing interactions between actors and use cases.
                          - Class Diagram: Models the system's structure by showing classes, attributes, methods, and their relationships.
                          - Sequence Diagram: Describes the flow of messages between objects in a specific use case or operation over time.
                          - Data Flow Diagram (DFD): Visualizes the flow of data within the system, showing how data is processed and transferred between components.
                        
                        - 'ProjectPath' indicates where the diagram should be implemented in the project structure, such as `docs/diagrams/architecture`.
                        
                        - The 'Mermaid Code' key should contain the Mermaid syntax for generating the diagram. It is essential that the code is properly formatted with line breaks, as without them, the code becomes unreadable and confusing. Line breaks help ensure that the Mermaid code is interpreted correctly, especially when stored in JSON format.
                        
                        Each diagram should integrate all provided classes into a cohesive representation, reflecting their relationships and interactions. Be specific and provide context in the descriptions, using relevant keywords related to software engineering.
                        
                        ### Example Output:
                        
                        [
                          {
                            "Name": "User Authentication Use Case",
                            "Type": "Use Case Diagram",
                            "Description": "This diagram represents the authentication process, showing the interactions between users, the login system, and the database.",
                            "Mermaid Code": "```mermaid\\nusecaseDiagram\\nactor User as 'User'\\nrectangle System {\\n  usecase Login as 'User Login'\\n  usecase Validate as 'Validate Credentials'\\n}\\nUser --> Login\\nLogin --> Validate\\nValidate --> Database\\n```",
                            "ProjectPath": "docs/diagrams/usecase"
                          },
                          {
                            "Name": "Core Class Structure",
                            "Type": "Class Diagram",
                            "Description": "This diagram represents the core classes of the application, including User, Role, and AuthService, and their relationships such as inheritance and associations.",
                            "Mermaid Code": "```mermaid\\nclassDiagram\\nclass User {\\n  +String id\\n  +String username\\n  +String password\\n}\\nclass Role {\\n  +String id\\n  +String name\\n}\\nclass AuthService {\\n  +authenticate(String username, String password)\\n}\\nUser --> Role\\nAuthService ..> User\\n```",
                            "ProjectPath": "docs/diagrams/class"
                          },
                          {
                            "Name": "User Login Sequence",
                            "Type": "Sequence Diagram",
                            "Description": "This diagram illustrates the flow of messages during the user login process, including interactions between the User, AuthService, and Database.",
                            "Mermaid Code": "```mermaid\\nsequenceDiagram\\nUser->>AuthService: Enter credentials\\nAuthService->>Database: Validate credentials\\nDatabase-->>AuthService: Return success/failure\\nAuthService-->>User: Grant access/deny\\n```",
                            "ProjectPath": "docs/diagrams/sequence"
                          },
                          {
                            "Name": "Data Processing Flow",
                            "Type": "Data Flow Diagram",
                            "Description": "This diagram visualizes the flow of data from the user input through the system's processing layers to the final storage in the database.",
                            "Mermaid Code": "```mermaid\\nflowchart TD\\nA[User Input] --> B[Data Validation]\\nB --> C[Processing]\\nC --> D[Database]\\n```",
                            "ProjectPath": "docs/diagrams/dataflow"
                          }
                        ]
                        """ +
                    "The output should be a JSON array of diagrams. Each diagram should have a 'Name', 'Type', 'Description', 'Mermaid Code' and 'ProjectPath'.  'Type' can be  'UseCase Diagram', 'Class Diagram', 'Sequence Diagram', or 'Data Flow Diagram'. 'ProjectPath' should indicate where the diagram should be implemented in the project structure. 'Mermaid Code' should contain the Mermaid code for the diagram. Be specific and provide context in the descriptions, using relevant keywords related to governmental audits." +
                    "For instance: [{'Name': 'Risk Management', 'Type': 'UseCase Diagram', 'Description': 'A use case diagram illustrating the interactions between auditors and the system to manage and analyze audit risks.', 'Mermaid Code': '```mermaid\ngraph LR\nAuditor --> (Manage Risks)\nAuditor --> (Analyze Risks)\n(Manage Risks) --> System\n(Analyze Risks) --> System\n```', 'ProjectPath': 'documentation/diagrams/risks'}, {'Name': 'Audit Recommendation Analysis', 'Type': 'Sequence Diagram', 'Description': 'A sequence diagram detailing the process of analyzing audit recommendations using a Generative AI model, including the data input, processing, and report generation steps.', 'Mermaid Code': '```mermaid\nsequenceDiagram\nAuditor->>+AI Model: Input Audit Data\nAI Model->>-Auditor: Generate Analysis Report\n```', 'ProjectPath': 'documentation/diagrams/recommendations'}]. " +
                    "Do not include any additional text or explanations.", description)).replaceAll("[\\r\\n]", " ");
        }
    }, USERMESSAGE {
        @Override
        public String generate(String currentMessage, String history, String baseInfo) {
            return ("You are an expert software engineer. Given a user message and the conversation history, provide a response that solves the user's, considering the conversation context. " +
                    "\n\nBase informations about the project:\n\n```\n" + baseInfo + "\n```\n\n" +
                    "\n\nConversation History:\n\n```\n" + history  + "\n```\n\n" +
                    "User Message:\n\n" + "```\n \n" + currentMessage + "```\n\n" +
                    "Follow these instructions to formulate your answer:\n\n" +
                    "* Analyze the conversation history to understand the user's problem. \n" +
                    "* Utilize prompt engineering techniques to formulate an accurate and relevant response.\n" +
                    "* If necessary, ask the user for more information to clarify the problem.\n" +
                    "* Focus on providing practical and helpful solutions based on your expertise in governmental audits. " +
                    "Do not include any additional text or explanations, " +
                    "the response needs to be only the response text. Do not include JSON formatting")
                    .replaceAll("[\\r\\n]", " ");
        }
    };

    protected abstract String generate(String description, String history, String baseInfo);

    public String data(String description) {
        return generate(description, "", "");
    }

    public String data(String currentMessage, String history, String baseInfo) {
        return generate(currentMessage, history, baseInfo);
    }
}
