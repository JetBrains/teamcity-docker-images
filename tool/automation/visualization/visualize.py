import os, sys, getopt
from argparse import ArgumentError
from collections import defaultdict
import matplotlib.pyplot as plt
import csv


class ImageStatistics:
    """
    Represents statistics of particular Docker Image reported by ...
    ... automation framework.
    """
    def __init__(self, repo: str, tag: str, date: str, size: str):
        self.repo = repo
        self.tag = tag
        try:
            self.date = date.split('T')[0]
        except:
            print(f"Date doesn't match the expected ISO pattern - yyyy-MM-dd'T'HH:mm:ss.SSSXXX. Assinging the provided one.")
            self.date = date

        try:
            # As specified in PEP 237, "long" had been "renamed" to "int", thus overflow is not expected.
            self.size = int(size) / 1000000
        except ValueError:
            raise ValueError(f"Incorrect, non-numeric value had been specified as Docker image size: {self.size}")

    
    def get_target_release_platform(self) -> str:
        """
        Returns target platform for TeamCity Docker image, without the specified prefix - ...
        ... usually the year of release. E.g.: "2022.04.1-linux" -> "linux"
        """
        try:
            return self.tag.split('-', 1)[1]
        except:
            print(f"Unable to parse {self.tag}")
            return f"release-without-tag"

        
    def get_release_version(self) -> str:
        """
        Returns version of TeamCity release.
        E.g.: "2022.04.1-linux" -> "2022.04.1"
        """
        try:
            return self.tag.split('-', 1)[0]
        except:
            print(f"Unable to parse {self.tag}")
            return self.tag
    
    
    def is_latest(self) -> bool:
        """
        Determines if release matches "latest" image within Dockerhub.
        """
        return self.tag.__contains__('latest')

            
    def __str__(self) -> str:
        return f"{self.date}-{self.repo}-{self.tag}-{self.size}"


def get_image_statistics_from_csv_line(csv_line_elements: list) -> ImageStatistics:
    """
    Constructs Docker Image statistics out of provided CSV line.
    :param csv_line_elements: - CSV line values split by chosen separator
    :returns: ImageStatistics instance if succeed, otherwise None
    """
    # 4 - minimal amount of elements within valuable payload
    if len(csv_line_elements) < 4:
        return None
    return ImageStatistics(repo=csv_line_elements[0],tag=csv_line_elements[1], date=csv_line_elements[2], size=csv_line_elements[3])


def get_image_statistics_file_file(file_path: str) -> dict:
    """
    Returns collection of ImageStatistics instances parsed from given file path.
    :param file_path: CSV file used as a datasource
    :returns: dictionary <image version> - [ImageStatistics]
    """
    data = defaultdict(defaultdict)
    with open(file_path) as file:
        reader_obj = csv.reader(file)
        for line in reader_obj:
            image_statistics = get_image_statistics_from_csv_line(line)
            if not image_statistics:
                continue
            # <tag> - [statistic1, statistic2, ... , statistic N]
            retrieved_data = data.get(image_statistics.repo, defaultdict())
            retrieved_data_list = retrieved_data.get(image_statistics.get_target_release_platform(), [])
            retrieved_data_list.append(image_statistics)
            data[image_statistics.repo][image_statistics.get_target_release_platform()] = retrieved_data_list
    return data


def plot_image_statistics_from_data(image_size_data: dict) -> None:
    """
    Plots charts of image statistics for each TeamCity repository.
    :param image_size_data: data encapsulating information about statistics of TeamCity Docker ...
    ... images located at different repositories: <repository-<tag, [statistics]>
    """
    # Plot chart for each repotisory (server, agent, etc.)
    for repo, repo_info in image_size_data.items():
        # Plot the chart for specific repository, e.g. "jetbrains/teamcity-server"
        fig, ax = plt.subplots()
        ax.ticklabel_format(useOffset=False)
        print(f"repo info: {repo_info}")
        for image_tag, image_statistics in repo_info.items():
            if image_tag == 'latest':
                continue
            x_values = []
            y_values = []
            for stat in image_statistics:
                print(stat)
                if not stat or stat.is_latest():
                    continue
                # we remove year from tag so mltiple images are displayed on the same chart 
                x_values.append(stat.get_release_version())
                y_values.append(stat.size)
            ax.plot(x_values, y_values, marker='o', label=image_tag)
            fig.autofmt_xdate(bottom=0.2, rotation=10, ha='right')

        plt.title(f"Image Size Trend \n {repo}")
        plt.xlabel('Image Version')
        plt.xticks(rotation=50)
        ax.grid(True)
        plt.ylabel('Size, MBs')
        plt.legend(loc='upper left') 
        fig.tight_layout()
        plt.show()


def main(argv):
    try:
        # -- parse CLI options
        opts, args = getopt.getopt(argv, 'hd:p:i:', ['directory='])
        print(opts)
        if not opts:
            raise getopt.GetoptError('Not enough arguments.')
    except (getopt.GetoptError, ArgumentError) as e:
        print(f"{e} \n Usage: python3 visualize.py -d <source directory>")
        sys.exit(2)
    
    # -- actions based on CLI options
    usage = 'python3 visualize.py -d <source directory>'
    source_directory = ''
    for opt, arg in opts:
        if opt == '-h':
            print(usage)
            sys.exit()
        elif opt == '-d':
            source_directory = arg
   
    folder = os.fsencode(source_directory)
    for file in os.listdir(folder):
        filename_decoded = file.decode('utf-8')
        filepath = f"{source_directory}/{filename_decoded}"

        image_statistics_from_file_dict = get_image_statistics_file_file(filepath)
        plot_image_statistics_from_data(image_statistics_from_file_dict)

if __name__ == "__main__":
   main(sys.argv[1:])

